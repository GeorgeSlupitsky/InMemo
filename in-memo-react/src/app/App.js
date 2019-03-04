import React, { Component } from 'react';
import './App.css';
import Home from '../components/home/Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import CDList from '../components/cds/CDList'
import CDEdit from '../components/cds/CDEdit'
import DrumStickList from '../components/drumsticks/DrumStickList'
import DrumStickEdit from '../components/drumsticks/DrumStickEdit'
import 'react-s-alert/dist/s-alert-default.css';
import 'react-s-alert/dist/s-alert-css-effects/slide.css';

class App extends Component {
    render() {
    return (
      <div>
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home}/>
            <Route path='/cds' exact={true} component={CDList}/>
            <Route path='/cds/:id' component={CDEdit}/>
            <Route path='/drumsticks' exact={true} component={DrumStickList}/>
            <Route path='/drumsticks/:id' component={DrumStickEdit}/>
          </Switch>
        </Router>
      </div>
    )
  }
}

export default App;
