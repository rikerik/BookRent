from flask import Flask, request, Response
from transformers import pipeline

app = Flask(__name__)
# Initializing the summarization pipeline with the BART model
summarizer = pipeline("summarization", model="facebook/bart-large-cnn")

# Defining the route for summarization
@app.route('/summarize', methods=['POST'])
def summarize():
    # Extract JSON data from the POST request
    data = request.get_json()
    # Extract the text field
    text = data.get('text', '')
    if not text:
        return Response("No text provided", status=400, mimetype='text/plain')
    # Generate the summary with the pipeline
    summary = summarizer(text, max_length=750, min_length=100, do_sample=True)
    # Extract the text from the summary
    summarized_text = summary[0]['summary_text']
    # Return the summarized text as plain text
    return Response(summarized_text, mimetype='text/plain')

# Run Flask app
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
