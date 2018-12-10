import React from 'react';
import Loader from '../loader/Loader';
import DonutChart from '../charts/donut-chart/DonutChart';
import DsvTable from '../charts/dsv-table/DsvTable';
import './TwitterSentimentAnalysis.css';
import { PROPERTIES } from './Properties'

class TwitterSentimentAnalysis extends React.Component {

    constructor() {
        super();
        this.state = {
            queryTerms: 'chicago pizza',
            tweetCount: '100',
            totals: '',
            isLoading: false
        };
        this.serviceUrl = this.getServiceUrl();
        this.changeHandler = this.changeHandler.bind(this);
        this.formHandler = this.formHandler.bind(this);
        this.render = this.render.bind(this);
        this.donutChart = {};
        this.dsvTable = {};
    }

    changeHandler(e) {
        let change = {}
        change[e.target.name] = e.target.value
        this.setState(change)
    }

    formHandler(event) {
        event.preventDefault();
        this.getSentimentAnalysis();
    }

    getServiceUrl() {

        return PROPERTIES.scheme + "://" + PROPERTIES.hostname + ":" + PROPERTIES.port + PROPERTIES.serviceUrl;

    }

    getSentimentAnalysis() {
        this.setState({ isLoading: true });

        fetch(this.serviceUrl + encodeURI(this.state.queryTerms) + "/" + this.state.tweetCount)
            .then(response => response.json())
            .then(
                (response) => {
                    this.donutChart = new DonutChart(response[0], "#resultsChart");
                    this.donutChart.d3Html();
                    this.dsvTable = new DsvTable(response[1], "~", "#resultsTable");
                    this.dsvTable.d3Html();
                    this.setState({
                        totals: JSON.stringify(response[0]),
                        isLoading: false
                    });
                },
                (error) => {
                    this.setState({
                        isLoading: false,
                        error
                    }
                    );
                }
            );

    }

    render() {

        return (
            <React.Fragment>
                {this.state.isLoading ?
                    <Loader />
                    : null}
                <div className="panel panel-primary" >

                    <div className="panel-body">

                        <form id="searchForm" onSubmit={this.formHandler} >
                            <fieldset>
                                <legend>Search</legend>
                                <div className="form-group">

                                    <label htmlFor="queryTerms" className="form-control-label">Query Terms</label>
                                    <input type="text"
                                        name="queryTerms"
                                        value={this.state.queryTerms}
                                        onChange={this.changeHandler}
                                        className="form-control form-control-lg"
                                        autoFocus="autofocus"
                                        tabIndex="1" />
                                </div>
                                <div className="form-group">
                                    <label htmlFor="tweetCount"
                                        className="form-control-label"
                                        id="tweetCountLabel"># of Tweets</label>
                                    <output name="tweetCountOutput"
                                        id="tweetCountOutput"> {this.state.tweetCount}</output>
                                    <input type="range"
                                        value={this.state.tweetCount}
                                        onChange={this.changeHandler}
                                        className="form-control"
                                        id="tweetCount"
                                        name="tweetCount"
                                        min="10" max="500" step="10"
                                        tabIndex="2" />
                                </div>

                                <input type="submit" className="btn btn-primary" tabIndex="3" />
                            </fieldset>
                        </form>
                        <hr className={this.state.totals.length > 0 ? 'show' : 'hide'}/>
                        <DonutChart />
                        <div id="totals">{this.state.totals}</div>
                        <hr className={this.state.totals.length > 0 ? 'show' : 'hide'}/>
                        <DsvTable />
                    </div>

                </div>
            </React.Fragment>
        )
    }
}

export default TwitterSentimentAnalysis;