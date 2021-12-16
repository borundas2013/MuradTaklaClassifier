from murad_takla_classifer import MuradTaklClassifer
import json
from flask import Flask,request
app = Flask(__name__)


@app.route('/predict')
def predict():
    file_name = "model/murad_takla_classifier.pkl"
    muradTaklCLF = MuradTaklClassifer(model_Path=file_name)
    print(request.args.get('q') )
    result, proba, jsonString = muradTaklCLF.predict([request.args.get('q')])
    return jsonString

if __name__ == '__main__':
    app.run()

# jsonString=predict('pasa asi pasa teko');
# jsonOb=json.loads(jsonString)
# print(jsonOb['prediction'])
# print(jsonOb['murad_takla_prob'])
# print(jsonOb['non_murad_takla_prob'])

#new_data=['pasa asi pasa teko','codom tura','How are you',"apna kmon asen","pani duklona Onk kosto","amar sathe jabe tumi khelte",'amara niba magi sate']
