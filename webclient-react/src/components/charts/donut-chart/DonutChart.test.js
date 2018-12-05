import React from 'react';
import ReactDOM from 'react-dom';
import DonutChart from './DonutChart';
import { RESPONSE } from '../../twitter-sentiment-analysis/MockTwitterResponse';

import * as d3 from 'd3';

describe("DonutChart", function () {
    var donutData = RESPONSE[0];
    var target = "#resultsChart";
    it('renders donut chart', function () {

        const div = document.createElement('div');
        let donutChart = new DonutChart(donutData, target);
        ReactDOM.render(<DonutChart />, div);

        expect(donutChart).toBeTruthy();

        donutChart.makeDonut();
        let donut = getDonut();
        expect(donut.size()).toBe(1);
        expect(getPaths().size()).toBe(3);
        expect(getLegends().size()).toBe(3);
        expect(getPathtip().empty()).toBe(false);

        ReactDOM.unmountComponentAtNode(div);
    });

    it('should do a mouseover event', () => {
        const div = document.createElement('div');
        let donutChart = new DonutChart(donutData, target);
        ReactDOM.render(<DonutChart />, div);

        expect(donutChart).toBeTruthy();
        donutChart.makeDonut();

        let path = getPath();
        let el = path.node();

        el.dispatchEvent(new MouseEvent('mouseover'));

        let pathtip = getPathtip();
        let pel = pathtip.node();

        let style = pel.getAttribute("style");
        expect(style).toBe("display: block;");

        el.dispatchEvent(new MouseEvent('mouseout'));

        style = pel.getAttribute("style");
        expect(style).toBe("display: none;");
    });

    function getDonut() {
        return d3.select("svg");
    }

    function getPaths() {
        return d3.selectAll("path");
    }

    function getPath() {
        return d3.select("path");
    }

    function getLegends() {
        return d3.selectAll("g.legend");
    }

    function getPathtip() {
        return d3.select("#pathtip");
    }
});