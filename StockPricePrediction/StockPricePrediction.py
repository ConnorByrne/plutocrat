import mysql.connector
import tensorflow as td
from tensorflow import keras
from keras.models import Sequential
from keras.layers import Dense
import numpy as np
import mysql.connector
import pandas_datareader.data as web
import datetime as dt
from datetime import date
import re
from sklearn.preprocessing import StandardScaler
from sklearn.preprocessing import OneHotEncoder
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
import matplotlib.pyplot as plt

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
    try:
        dateInt = re.findall(r'\d+',dateString)
        start = dt.datetime(int(dateInt[0]),int(dateInt[1]),int(dateInt[2]))
        end = date.today()
        df=web.get_data_yahoo(symbol,start=start,end=end)
        return df.head(1)
    except:
        return "Error"

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
    print (x)
    
#trainCompanies = companies[:int(len(companies)*.8)]
#testCompanies = companies[int(len(companies)*.8):]

#trainYahoo = yahoo[:int(len(yahoo)*.8)]
#testYahoo = yahoo[int(len(yahoo)*.8):]

companiesArray = np.asarray(companies)
yahooArray = np.asarray(yahoo)

#testYahooArray = np.asarray(testYahoo)
#testCompaniesArray = np.asarray(testYahoo)

sc=StandardScaler()
yahooArray=sc.fit_transform(yahooArray)
companiesArray=sc.fit_transform(companiesArray)

ohe=OneHotEncoder()

companiesArray=ohe.fit_transform(companiesArray).toarray()
yahooArray=ohe.fit_transform(yahooArray).toarray()

x_train,x_test,y_train,y_test = train_test_split(companiesArray,yahooArray,test_size=0.1)

model = Sequential()
model.add(Dense(16, input_dim=20, activation='relu'))
model.add(Dense(12, activation='relu'))
model.add(Dense(4,activation='softmax'))

model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])

history = model.fit(x_train,y_train,epochs=40,batch_size=64)

y_pred = model.predict(x_test)
pred=list()

for i in range(len(y_pred)):
    pred.append(np.argmax(y_pred[i]))

test=list()
for i in range(len(y_test)):
    test.append(np.atgmax(y_test[i]))

a=accuracy_score(pred,test)
print("Accuracy is:", a*100)

history = model.fit(x_train, y_train, validation_data=(x_test,y_test), epochs=100, batch_size=64)

plt.plot(history.history['acc'])
plt.plot(history.history['val_acc'])
plt.title('Model accuracy')
plt.ylabel('Accuracy')
plt.xlabel('Epoch')
plt.legend(['Train', 'Test'], loc'upper left')
plt.show()
#model = keras.Sequential()
#model.add(keras.layers.Flatten(input_shape=(len(companies),len(yahoo))))
#model.add(keras.layers.Embedding(10000,16))
#model.add(keras.layers.GlobalAveragePooling1D())
#model.add(keras.layers.Dense(16, activation="relu"))
#model.add(keras.layers.Dense(1,activation="sigmoid"))

#model.summary()
#model.compile(optimizer = "adam", loss="mean_squared_error", metrics=["accuracy"])

#model.fit(companiesArray,yahooArray,epochs=40)

#predict = model.predict(testCompanies,testYahoo)
#test_acc = model.evaluate(testCompaniesArray,testYahooArray)
#print("tested accuracy:", test_acc)

