import React from 'react';
import ReactDOM from 'react-dom';
import DsvTable from './DsvTable';
import { RESPONSE } from '../../twitter-sentiment-analysis/MockTwitterResponse';
import TwitterSentimentAnalysis from '../../twitter-sentiment-analysis/TwitterSentimentAnalysis';
import * as d3 from 'd3';

describe("DSVTable", function () {
    var dsvdata = RESPONSE[1].toString();
    var delimiter = "~";
    var target = "resultsTable";
    it("renders table", function () {
        var component = new DsvTable(dsvdata, delimiter, target);
        const div = document.createElement('div');
        ReactDOM.render(<TwitterSentimentAnalysis/>, div);
        component.d3Html();
       
        expect(getTable().empty()).toBe(false);
        var table = getTable();
        expect(table.attr('class')).toBe("table table-striped");
        expect(getTable().size()).toBe(1);
        expect(getRows().size()).toBe(100);
        expect(getHeaders().size()).toBe(1);
        
        var elt = getHeaders().html();
        var headerString =
        "<tr><th>sentiment</th><th>tweet</th><th>positive</th><th>negative</th><th>neutral</th></tr>";
        expect(elt).toBe(headerString);

        ReactDOM.unmountComponentAtNode(div);
    });

    it('should do a click event', () => {
        var component = new DsvTable(dsvdata, delimiter, target);
        const div = document.createElement('div');
        ReactDOM.render(<TwitterSentimentAnalysis/>, div);
        component.d3Html();
        
        let el = document.querySelector('.table > thead:nth-child(2) > tr:nth-child(1) > th:nth-child(3)');
        el.dispatchEvent(new MouseEvent('click'));
        
        let clazz = el.getAttribute("class");
        expect(clazz).toBe("aes");
    
        el.dispatchEvent(new MouseEvent('click'));
        clazz = el.getAttribute("class");
        expect(clazz).toBe("des");
      });

    function getTable() {
        return  d3.select(".table");
      }
    
      function getHeaders() {
        return  d3.select(".table > thead");
      }
    
      function getRows() {
        return  d3.selectAll(".table > tbody > tr");
      }
});

