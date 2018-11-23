import React, { Component } from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import './App.css';
import About from './components/about/About';
import Navigation from './components/navbar/Navigation';
import TwitterSentimentAnalysis from './components/twitter-sentiment-analysis/TwitterSentimentAnalysis';

class App extends Component {

  render() {
    return (
      <BrowserRouter>
        <div className="container" >
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
