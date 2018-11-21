import React from 'react';



class TwitterSentimentAnalysis extends React.Component {
    render() {
        return (
            <div class="panel panel-primary" >

                <div class="panel-body">

                    <form id="searchForm">
                        <fieldset>
                            <legend>Search</legend>
                            <div class="form-group">
                                <label for="queryTerms" class="form-control-label">Query Terms</label>
                                <input type="text"

                                    name="queryTerms"
                                    class="form-control form-control-lg"
                                    autofocus="autofocus"
                                    tabindex="1" />
                            </div>
                            <div class="form-group">
                                <label for="tweetCount" class="form-control-label"># of Tweets</label>
                                <output name="tweetCountOutput" id="tweetCountOutput"></output>
                                <input type="range"
                                    class="form-control"
                                    id="tweetCount"
                                    name="tweetCount"
                                    min="10" max="500" step="10" oninput="tweetCountOutput.value = tweetCount.value"
                                    tabindex="2" />
                            </div>

                            <input type="submit" class="btn btn-primary" tabindex="3" />
                        </fieldset>
                    </form>
                    <div id="resultsChart"></div>

                    <hr />
                    <div id="resultsTable"></div>
                </div>

            </div>
        )
    }
}

export default TwitterSentimentAnalysis;