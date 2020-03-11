import mysql.connector
import tensorflow as td
from tensorflow import keras
#from keras.models import Sequential
#from keras.layers import Dense
import numpy as np
import mysql.connector
import pandas_datareader.data as web
import datetime as dt
from datetime import date
from datetime import timedelta
import pandas as pd
import re
from sklearn.preprocessing import StandardScaler
from sklearn.preprocessing import OneHotEncoder
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
import matplotlib.pyplot as plt
from string import ascii_lowercase
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
        end = start
        df=web.get_data_yahoo(symbol,start=start,end=end)
        prediction = pd.to_numeric(df['Close'])
        price1=prediction.iloc[0]
        start = start+timedelta(days=1)
        end = start
        df=web.get_data_yahoo(symbol,start=start,end=end)
        prediction = pd.to_numeric(df['Close'])
        price2=prediction.iloc[0]
        priceChange= price1-price2
        return priceChange
    except:
        return 0.00

def alphabet_position(text):
    LETTERS={letter: str(index) for index, letter in enumerate(ascii_lowercase, start=1)}
    text=text.lower()
    #numbers=[LETTERS[character] for character in text if character in LETTERS elif isDigit(character)]
    numbers=[]
    for character in text:
        if character in LETTERS:
            numbers.append(LETTERS[character])
        elif character.isdigit():
            numbers.append(character)
    return int(''.join(numbers))

myCursor = db.cursor()

myCursor.execute("SELECT* FROM company, sentiment, company_sentiment WHERE(company.id=company_sentiment.Company_id) AND (sentiment.id=company_sentiment.sentimentData_id)")
companies=[]
yahoo = []
for x in myCursor:
    #companyid=x[0]
    #name=x[1]
    #symbol=x[2]
    #sentimentid=x[3]
    #sentimentdata=x[4]
    #sentimentdate=x[5]
    #company = (x[0],alphabet_position(x[4]),alphabet_position(x[5]))
    companies.append(alphabet_position(x[4]))
    dateString = (x[5])
    if(dateString[0]=="0"):
        dateString = "2"+dateString
    symbol=x[2]
    #yaho=(x[0],alphabet_position(x[5]),alphabet_position(getPrice(symbol,dateString)))
    yahoo.append(getPrice(symbol,dateString))
    print(alphabet_position(x[4]))
    
trainCompanies = companies[:int(len(companies)*.8)]
testCompanies = companies[int(len(companies)*.8):]

trainYahoo = yahoo[:int(len(yahoo)*.8)]
testYahoo = yahoo[int(len(yahoo)*.8):]

companiesArray = np.asarray(companies)
yahooArray = np.asarray(yahoo)

testYahooArray = np.asarray(testYahoo)
testCompaniesArray = np.asarray(testYahoo)

#sc=StandardScaler()
#yahooArray=sc.fit_transform(yahooArray.reshape(-1,1))
#companiesArray=sc.fit_transform(companiesArray)

#ohe=OneHotEncoder()

#companiesArray=ohe.fit_transform(companiesArray).toarray()
#yahooArray=ohe.fit_transform(yahooArray).toarray()

#x_train,x_test,y_train,y_test = train_test_split(companiesArray,yahooArray,test_size=0.1)

model = keras.Sequential()
#model.add(keras.layers.Flatten(input_shape=(28,28)))
model.add(keras.layers.Dense(16, input_dim=1, activation='relu'))
model.add(keras.layers.Dense(12, activation='relu'))
model.add(keras.layers.Dense(4,activation='softmax'))

model.summary()
model.compile(loss='sparse_categorical_crossentropy', optimizer='adam', metrics=['accuracy'])

history = model.fit(companiesArray,yahooArray,epochs=40,batch_size=64)

test_acc = model.evaluate(testCompaniesArray,testYahooArray)

print("Tested Accuracy: ", test_acc)
#y_pred = model.predict(x_test)
#pred=list()

#for i in range(len(y_pred)):
 #   pred.append(np.argmax(y_pred[i]))

#test=list()
#for i in range(len(y_test)):
 #   test.append(np.argmax(y_test[i]))

#a=accuracy_score(pred,test)
#print("Accuracy is:", a*100)

#history = model.fit(x_train, y_train, validation_data=(x_test,y_test), epochs=100, batch_size=64)

#plt.plot(history.history['acc'])
#plt.plot(history.history['val_acc'])
#plt.title('Model accuracy')
#plt.ylabel('Accuracy')
#plt.xlabel('Epoch')
#plt.legend(['Train', 'Test'], loc='upper left')
#plt.show()
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

