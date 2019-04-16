import React, { Component } from 'react'
import './App.css'
import Home from '../components/home/Home'
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import CDEdit from '../components/items/CDEdit'
import DrumStickEdit from '../components/items/DrumStickEdit'
import 'react-s-alert/dist/s-alert-default.css'
import 'react-s-alert/dist/s-alert-css-effects/slide.css'
import ItemList from '../components/items/ItemList'

class App extends Component {
  render() {
    const WrappedCDListForAll = function (props) {
      return (<ItemList {...props} group='all' getURL='api/cds' deleteURL='/api/cd/' />)
    }
    const WrappedCDListForForeign = function (props) {
      return (<ItemList {...props} group='foreign' getURL='api/cdsForeign' deleteURL='/api/cd/' />)
    }
    const WrappedCDListForDomestic = function (props) {
      return (<ItemList {...props} group='domestic' getURL='api/cdsDomestic' deleteURL='/api/cd/' />)
    }
    const WrappedDrumStickList = function (props) {
      return (<ItemList {...props} group='drumsticks' getURL='api/drumsticks' deleteURL='/api/drumstick/' />)
    }
    const WrappedCDEditForAll = function (props) {
      return (<CDEdit {...props} url='/cds' />)
    }
    const WrappedCDEditForForeign = function (props) {
      return (<CDEdit {...props} url='/cdsForeign' />)
    }
    const WrappedCDEditForDomestic = function (props) {
      return (<CDEdit {...props} url='/cdsDomestic' />)
    }
    return (
      <div>
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home} />
            <Route path='/cds' exact={true} component={WrappedCDListForAll} />
            <Route path='/cdsForeign' exact={true} component={WrappedCDListForForeign} />
            <Route path='/cdsDomestic' exact={true} component={WrappedCDListForDomestic} />
            <Route path='/cds/:id' component={WrappedCDEditForAll} />
            <Route path='/cdsForeign/:id' component={WrappedCDEditForForeign} />
            <Route path='/cdsDomestic/:id' component={WrappedCDEditForDomestic} />
            <Route path='/drumsticks' exact={true} component={WrappedDrumStickList} />
            <Route path='/drumsticks/:id' component={DrumStickEdit} />
          </Switch>
        </Router>
      </div>
    )
  }
}

export default App
