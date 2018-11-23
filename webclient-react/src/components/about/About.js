import React from 'react';



class About extends React.Component {

    render() {
        return (
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h1>About</h1>
                </div>
                <p>Machine Learning, Jakarta EE, React, Bootstrap, D3 </p>
                <p>The github repository for this project is located
        <a href="https://github.com/hkastler/text-analysis-project"
                        rel="noopener noreferrer" target="_blank"> here.</a></p>
            </div>
        )
    }
}

export default About;