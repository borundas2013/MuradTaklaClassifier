from json.encoder import JSONEncoder
import numpy as np
import pandas as pd
import re
import pickle
import json


class MuradTaklClassifer(JSONEncoder):
    def __init__(self,model_Path):
      self.model_path=model_Path

    def _cleanText(self,dataframe):
      corpus=[]
      for i in range(dataframe.shape[0]):
        clean_text=re.sub('\[a-zA-Z]','',dataframe['Text'][i])
        clean_text=re.sub(r'[^\w\s]',' ',clean_text)
        clean_text=clean_text.lower()
        corpus.append(clean_text)
      dataframe['Clean_Text']=corpus
      return dataframe


    def predict(self,prediction_data):
      with open(self.model_path, 'rb') as file:
        pickle_model,c_vectorizer = pickle.load(file)
      dataframe=pd.DataFrame(prediction_data,columns=['Text'])
      cleanDataFrame= self._cleanText(dataframe)
      result=pickle_model.predict(c_vectorizer.transform(cleanDataFrame['Clean_Text']))
      result_prob=pickle_model.predict_proba(c_vectorizer.transform(cleanDataFrame['Clean_Text']))
      jsonString={"prediction":result.tolist(),"prediction_probability":result_prob.tolist()}
      return result, result_prob, json.dumps(jsonString)