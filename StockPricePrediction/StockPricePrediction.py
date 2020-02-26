import mysql.connector
import tensorflow as td
from tensorflow import keras
import numpy as np
import mysql.connector
import pandas_datareader.data as web
import datetime as dt
from datetime import date
import re


db = mysql.connector.connect(
    host="localhost",
    user="root",
    passwd="root",
    database = "dt354") 

class Company:
    sentiment = []
    def __init__(self,name,ticker):
        self.name = name
        self.ticker=ticker
    
    def addSentiment(self,data):
        self.sentiment.append(data)
    

def getPrice(symbol,dateString):
    
    dateInt = re.findall(r'\d+',dateString)
    start = dt.datetime(int(dateInt[0]),int(dateInt[1]),int(dateInt[2]))
    end = date.today()
    df=web.get_data_yahoo(symbol,start=start,end=end)
    return df.head(1)

myCursor = db.cursor()

myCursor.execute("SELECT* FROM company, sentiment, company_sentiment WHERE(company.id=company_sentiment.Company_id) AND (sentiment.id=company_sentiment.sentimentData_id)")
companies=[]
yahoo = []
for x in myCursor:
    companies.append(x)
    dateString = (x[5])
    if(dateString[0]=="0"):
        dateString = "2"+dateString
    symbol=x[2]
    yahoo.append((x[1],x[5],getPrice(symbol,dateString)))
    
trainCompanies = companies[:int(len(companies)*.8)]
testCompanies = companies[int(len(companies)*.8):]

trainYahoo = yahoo[:int(len(yahoo)*.8)]
testYahoo = yahoo[int(len(yahoo)*.8):]

model = keras.Sequential()
#model.add(keras.layers.Flatten(input_shap=(len(companies),len(yahoo))))
model.add(keras.layers.Embesdding(10000,16))
model.add(keras.layers.GlobalAveragePooling1D())
model.ass(keras.layers.Dense(16, activation="relu"))
model.add(keras.layers.Dense(1,activation="sigmoid"))

model.summary()
model.compile(optimizer = "adam", loss="sparse_categorical_crossentrophy", metrics=["accuracy"])

model.fit(trainCompanies,trainYahoo,epochs=40)

#predict = model.predict(testCompanies,testYahoo)
test_acc = model.evaluate(testCompanies,testYahoo)
print("tested accuracy:", test_acc)

