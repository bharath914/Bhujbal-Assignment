from flask import Flask, render_template, request

app = Flask(__name__)

@app.route('/')
def home():
    return render_template('index.html')

@app.route('/submit', methods=['POST'])
def submit():
    if request.method == 'POST':
        name = request.form.get('name')
        num = request.form.get('num')
        from_place = request.form.get('from_place')
        to_place = request.form.get('to_place')
        adult = int(request.form.get('adult'))
        child = int(request.form.get('child'))
        amount = 0
        
        if (from_place == "kakinada" and to_place == "vizag") or (from_place == "vizag" and to_place == "kakinada"):
            amount = (500 * adult) + (250 * child)
        elif (from_place == "kakinada" and to_place == "anakapalli") or (from_place == "anakapalli" and to_place == "kakinada"):
            amount = (400 * adult) + (200 * child)    
        elif (from_place == "kakinada" and to_place == "tuni") or (from_place == "tuni" and to_place == "kakinada"):
            amount = (300 * adult) + (150 * child)
        elif (from_place == "kakinada" and to_place == "annavaram") or (from_place == "annavaram" and to_place == "kakinada"):
            amount = (250 * adult) + (100 * child)
        elif (from_place == "annavaram" and to_place == "vizag") or (from_place == "vizag" and to_place == "annavaram"):
            amount = (400 * adult) + (200 * child)
        elif (from_place == "annavaram" and to_place == "anakapalli") or (from_place == "anakapalli" and to_place == "annavaram"):
            amount = (150 * adult) + (100 * child)
        elif (from_place == "annavaram" and to_place == "tuni") or (from_place == "tuni" and to_place == "annavaram"):
            amount = (50 * adult) + (25 * child)
        elif (from_place == "tuni" and to_place == "vizag") or (from_place == "vizag" and to_place == "tuni"):
            amount = (250 * adult) + (150 * child)
        elif (from_place == "tuni" and to_place == "anakapalli") or (from_place == "anakapalli" and to_place == "tuni"):
            amount = (100 * adult) + (50 * child)
        elif (from_place == "anakapalli" and to_place == "vizag") or (from_place == "vizag" and to_place == "anakapalli"):
            amount = (250 * adult) + (150 * child)
        else:
            return render_template('index.html')
        
        return render_template('result.html', name=name, num=num, from_place=from_place, to_place=to_place, adult=adult,child=child,amount=amount)

if __name__ == '__main__':
    app.run(debug=True)


