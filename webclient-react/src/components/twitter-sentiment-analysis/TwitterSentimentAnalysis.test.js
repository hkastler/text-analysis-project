import React from 'react';
import ReactDOM from 'react-dom';
import TwitterSentimentAnalysis from './TwitterSentimentAnalysis';

it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<TwitterSentimentAnalysis />, div);
  ReactDOM.unmountComponentAtNode(div);
});