import mysql.connector
import tensorflow as td
from tensorflow import keras
import numpy as np
from yahoo_finance import Share

class Company:
    sentiments=[]
    def __init__(self,id,name, tickerSymbol,):
        self.id=id
        self.name=name,
        self.tickerSymbol=tickerSymbol

    def addSentiment(self,sentiemnt):
        self.sentiments.append(sentiemnt)

db = mysql.connector.connect(
    host="localhost",
    user="root",
    passwd="root",
    database = "dt354") 


myCursor = db.cursor()

#myCursor.execute("SELECT* FROM company, sentiment, company_sentiment WHERE(company.id=company_sentiment.Company_id) AND (sentiment.id=company_sentiment.sentimentData_id)")
myCursor.execute("SELECT id FROM company")
companies=[]
yahoo =[]
for x in myCursor:
    id = x
    name = myCursor.execute("SELECT companyName FROM company WHERE id="+id)
    tickerSymbol = myCursor.execute("SELECT tickerSymbol FROM company)
    c1=Company(id,name,tickerSymbol)
    for y in tickerSymbol:
        yahoo.append(Share(tickerSymbol))
    sentiments = myCursor.execute("SELECT sentiment.data FROM company, sentiment, company_sentiment where company.id="+id+" AND (company.id=company_sentiment.Company_id) AND(sentiment.id=company_sentiment.sentimentData_id)")
    for y in sentiments:
        c1.addSentiment(y)
    companies.append(c1)



model = keras.Sequential()
model.add(keras.layers.Embesdding(10000,16))
model.add(keras.layers.GlobalAveragePooling1D())
model.ass(keras.layers.Dense(16, activation="relu"))
model.add(keras.layers.Dense(1,activation="sigmoid"))

model.summary()
model.compile(optimizer = "adam", loss="sparse_categorical_crossentrophy", metrics=["accuracy"])

model.fit(myCursor,yahoo,epochs=40)

predict = model.predict(yahoo)

print(predict)

