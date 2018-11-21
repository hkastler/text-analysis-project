import React, { Component } from 'react';
import './App.css';
import { BrowserRouter, Route } from 'react-router-dom'
import Navigation from './components/navbar/Navigation';
import TwitterSentimentAnalysis from './components/twitter-sentiment-analysis/TwitterSentimentAnalysis';
import About from './components/about/About';

class App extends Component {
  render() {
    return (
      <BrowserRouter>
        <div className="App" class="container">
          <Navigation />
          <Route exact component={TwitterSentimentAnalysis} path="/" />
          <Route component={TwitterSentimentAnalysis} path="/home" />
          <Route component={About} path="/about" />
        </div>
      </BrowserRouter>
    );
  }
}

export default App;
