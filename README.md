# Stock Market Trend Analyzer 📈 (Java + Gemini AI)

This is a personal project I built using Java and Spring Boot to help analyze stock market trends. It pulls historical stock data using Alpha Vantage and then uses Gemini AI to interpret that data and give insight into stock performance. The final output is generated as an Excel file that includes both the raw data and AI commentary.

---

## What It Does

- You enter a stock symbol (like `AAPL` or `GOOGL`) through a simple HTTP request.
- The app fetches past stock prices using Alpha Vantage (free tier).
- It then sends that data to Gemini AI, which responds with a plain-English summary of the trend (e.g., "The stock has shown consistent growth since March").
- Finally, it compiles everything into an Excel file and offers it as a download.

---

## Why I Built This

I wanted to explore combining AI with finance data in a practical way. This project helped me learn about:

- Working with real-world stock APIs (Alpha Vantage)
- Using Google's Gemini model for natural language responses
- Generating downloadable Excel reports in Java
- Handling exceptions and rate limits cleanly

---

## How to Use It

1. Clone the repo and run the Spring Boot app:
   ```bash
   ./mvnw spring-boot:run
2. Make a GET request to:
   ```bash
   http://localhost:8080/stock/predict?symbol=AAPL
3.You'll get an Excel file with two sheets:
  - One with historical prices
  - One with Gemini’s take on the trend

## API Keys
To use this project, you’ll need to set these two environment variables:

    export ALPHA_API_KEY=your_alpha_vantage_key
    export GEMINI_API_KEY=your_gemini_api_key

**Note:** Keep your API keys private. Don’t upload them to GitHub.

## Important Information
- The free version of Alpha Vantage has rate limits. If you're getting an error, wait a minute and try again.
- The Gemini-generated analysis is helpful, but not investment advice.
- I built this just to showcase how AI and finance data can work together in Java.

## Legal Stuff
This is a non-commercial, educational project. I’ve used the free tier of both Gemini and Alpha Vantage, following their terms of use. If you plan to scale or monetize this, make sure to check their licenses.

## About Me
I’m a software developer with 6 years of experience. This project is part of a portfolio I’m building to explore AI use cases and showcase modern Java development practices.


 


  
