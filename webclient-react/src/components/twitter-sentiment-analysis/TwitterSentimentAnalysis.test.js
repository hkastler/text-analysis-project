import React from 'react';
import ReactDOM from 'react-dom';
import ReactTestUtils from 'react-dom/test-utils';
import TwitterSentimentAnalysis from './TwitterSentimentAnalysis';
import DonutChart from '../charts/donut-chart/DonutChart';
import DsvTable from '../charts/dsv-table/DsvTable';
import { RESPONSE } from './MockTwitterResponse';


it('renders without crashing', () => {
  const div = document.createElement('div');
  ReactDOM.render(<TwitterSentimentAnalysis />, div);
  ReactDOM.unmountComponentAtNode(div);
});


it('should handle data correctly', () => {


  let twitterSentimentAnalysis = new TwitterSentimentAnalysis();

  expect(twitterSentimentAnalysis).toBeTruthy();
  expect(twitterSentimentAnalysis.state.queryTerms).toBe("chicago pizza");

  twitterSentimentAnalysis.state.tweetCount = "10";
  expect(twitterSentimentAnalysis.state.tweetCount).toBe("10");

  var rendered = ReactTestUtils.renderIntoDocument(<TwitterSentimentAnalysis />);
  //console.log(rendered);
  var donut = ReactTestUtils.findRenderedComponentWithType(rendered, DonutChart);
  expect(donut).toBeTruthy();
  var dsv = ReactTestUtils.findRenderedComponentWithType(rendered, DsvTable);
  
  expect(dsv).toBeTruthy();
  var form = ReactTestUtils.findRenderedDOMComponentWithTag(rendered, 'form');
  
  var inputs = ReactTestUtils.scryRenderedDOMComponentsWithTag(rendered, "input");
  expect(inputs.length).toBe(3);

  var divs = ReactTestUtils.scryRenderedDOMComponentsWithTag(rendered, "div");
  console.log(divs.length);
  

  //good request
  let res = RESPONSE;
  global.fetch = jest.fn().mockImplementation(() => {
    var p = new Promise((resolve, reject) => {
      resolve({
        json: function () {
          return res;
        }
      });
    });

    return p;
  });
  ReactTestUtils.Simulate.submit(form);
  
  //bad request
  global.fetch = jest.fn().mockImplementation(() => {
    var p = new Promise((resolve, reject) => {
      resolve(res);
    });

    return p;
  });
  ReactTestUtils.Simulate.submit(form);
});