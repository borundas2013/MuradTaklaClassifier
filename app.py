from murad_takla_classifer import MuradTaklClassifer
import json
from flask import Flask
app = Flask(__name__)


@app.route('/predict/<text>')
def predict(text):
    file_name = "model/murad_takla_classifier.pkl"
    muradTaklCLF = MuradTaklClassifer(model_Path=file_name)
    result, proba, jsonString = muradTaklCLF.predict([text])
    return jsonString

if __name__ == '__main__':
    app.run()

# jsonString=predict('pasa asi pasa teko');
# jsonOb=json.loads(jsonString)
# print(jsonOb['prediction'])
# print(jsonOb['prediction_probability'])

#new_data=['pasa asi pasa teko','codom tura','How are you',"apna kmon asen","pani duklona Onk kosto","amar sathe jabe tumi khelte",'amara niba magi sate']
