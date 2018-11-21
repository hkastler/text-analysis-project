import React, { Component } from 'react';
import './App.css';
import Navigation from './components/navbar/Navigation';
import TwitterSentimentAnalysis from './components/twitter-sentiment-analysis/TwitterSentimentAnalysis';

class App extends Component {
  render() {
    return (
      <div className="App" class="container">
        <Navigation/>
        <TwitterSentimentAnalysis/>
      </div>
    );
  }
}

export default App;
