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

#database connector
db = mysql.connector.connect(
    host="localhost",
    user="root",
    passwd="root",
    database = "dt354") 

    
def getPrice(symbol,dateString):
    try:
        #spliting dateString in day, month, and year values
        dateInt = re.findall(r'\d+',dateString)
        #setting start date using day, month, year values
        start = dt.datetime(int(dateInt[0]),int(dateInt[1]),int(dateInt[2]))
        end = start
        #datafram financial data for that date
        df=web.get_data_yahoo(symbol,start=start,end=end)
        #isolating close price
        prediction = pd.to_numeric(df['Close'])
        price1=prediction.iloc[0]

        #getting financial data for the next day.
        start = start+timedelta(days=1)
        end = start
        df=web.get_data_yahoo(symbol,start=start,end=end)
        prediction = pd.to_numeric(df['Close'])
        price2=prediction.iloc[0]
        #calculating and returning the price change
        priceChange= price1-price2
        return priceChange
    except:
        return 0.00

def alphabet_position(text):
    LETTERS={letter: str(index) for index, letter in enumerate(ascii_lowercase, start=1)}
    text=text.lower()
    numbers=[]
    for character in text:
        if character in LETTERS:
            numbers.append(LETTERS[character])
        elif character.isdigit():
            numbers.append(character)
    return int(''.join(numbers))

myCursor = db.cursor()
#getting data from database
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

    #getting digit representation of semtiment
    #companies.append(alphabet_position(x[4]))
    #companies.append([4])
    companies.append([6])
    dateString = (x[5])
    #adding 2 to begining of dates that got cut off
    if(dateString[0]=="0"):
        dateString = "2"+dateString
    symbol=x[2]
    #getting price change
    yahoo.append(getPrice(symbol,dateString))
    print(alphabet_position(x[4]))
    
#spilting data into training and testing data    
trainCompanies = companies[:int(len(companies)*.8)]
testCompanies = companies[int(len(companies)*.8):]

trainYahoo = yahoo[:int(len(yahoo)*.8)]
testYahoo = yahoo[int(len(yahoo)*.8):]

#converting arrays to numpy arrays
trainCompaniesArray = np.asarray(companies, dtype=float)
trainYahooArray = np.asarray(yahoo, dtype=float)

testYahooArray = np.asarray(testYahoo, dtype=float)
testCompaniesArray = np.asarray(testYahoo, dtype=float)

#padding and triming so that every entry is the same size
#trainCompanies = keras.preprocessing.sequence.pad_sequences(trainCompanies, value=0, padding="post", maxlen=250)
#testCompanies = keras.preprocessing.sequence.pad_sequences(testCompanies, value=0, padding="post", maxlen=250)


model = keras.Sequential()
model.add(keras.layers.Dense(units=1, input_shape=[1]))
#model.add(keras.layers.Embedding(10000,16))
model.compile(optimizer="sgd", loss="mean_squared_error")
model.fit(trainCompaniesArray,trainYahooArray,epochs=500)
test_acc=model.evaluate(testCompaniesArray,testYahooArray)
print(test_acc)
#model.add(keras.layers.Embedding(10000,16))
#model.add(keras.layers.GlobalAveragePooling1D())
#model.add(keras.layers.Dense(16, input_dim=1, activation='relu'))
#model.add(keras.layers.Dense(12, activation='relu'))
#model.add(keras.layers.Dense(4,activation='softmax'))

#model.summary()
#model.compile(loss='sparse_categorical_crossentropy', optimizer='adam', metrics=['accuracy'])

#history = model.fit(trainCompaniesArray,trainYahooArray,epochs=40,batch_size=64)

#test_acc = model.evaluate(testCompaniesArray,testYahooArray)

#print("Tested Accuracy: ", test_acc)


