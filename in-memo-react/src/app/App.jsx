import React, { Component } from 'react'
import './App.css'
import { BrowserRouter as Router, Route, Switch, Redirect } from 'react-router-dom'
import NotFoundPage from '../components/common/not-found-page/NotFoundPage'
import CDEditContainer from '../components/items/cd-edit/CDEditContainer'
import DrumStickEditContainer from '../components/items/drumstick-edit/DrumStickEditContainer'
import 'react-s-alert/dist/s-alert-default.css'
import 'react-s-alert/dist/s-alert-css-effects/slide.css'
import { ServiceContextProvider } from '../components/service-context/ServiceContext'
import { Provider } from 'react-redux'
import { createStore, applyMiddleware, compose } from 'redux'
import rootReducer from './../store/reducers'
import Service from './../services/Service'
import ItemListContainer from './../components/items/item-list/ItemListContainer'
import thunk from 'redux-thunk'
import Home from './../components/home/Home'
import LocalizedStrings from 'localized-strings'
import { getLocalizedMessages } from '../messages/locales/localizedMessages'

const composeEnhancers =
  typeof window === 'object' &&
    window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ ?
    window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({
    }) : compose

const store = createStore(
  rootReducer,
  composeEnhancers(
    applyMiddleware(thunk)
  )
)
const service = new Service()

class App extends Component {

  render() {
    let localizedMessages = new LocalizedStrings(getLocalizedMessages())

    const WrappedCDListForAll = function (props) {
      return (<ItemListContainer {...props} group='all' getURL='api/cds' deleteURL='/api/cd/' localizedMessages={localizedMessages} />)
    }
    const WrappedCDListForForeign = function (props) {
      return (<ItemListContainer {...props} group='foreign' getURL='api/cdsForeign' deleteURL='/api/cd/' localizedMessages={localizedMessages} />)
    }
    const WrappedCDListForDomestic = function (props) {
      return (<ItemListContainer {...props} group='domestic' getURL='api/cdsDomestic' deleteURL='/api/cd/' localizedMessages={localizedMessages} />)
    }
    const WrappedDrumStickList = function (props) {
      return (<ItemListContainer {...props} group='drumsticks' getURL='api/drumsticks' deleteURL='/api/drumstick/' localizedMessages={localizedMessages} />)
    }
    const WrappedCDEditForAll = function (props) {
      return (<CDEditContainer {...props} url='/cds' localizedMessages={localizedMessages} itemId={props.match.params.id} />)
    }
    const WrappedCDEditForForeign = function (props) {
      return (<CDEditContainer {...props} url='/cdsForeign' localizedMessages={localizedMessages} itemId={props.match.params.id} />)
    }
    const WrappedCDEditForDomestic = function (props) {
      return (<CDEditContainer {...props} url='/cdsDomestic' localizedMessages={localizedMessages} itemId={props.match.params.id} />)
    }
    const WrappedDrumStickEdit = function (props) {
      return (<DrumStickEditContainer {...props} localizedMessages={localizedMessages} itemId={props.match.params.id} />)
    }
    return (
      <div>
        <Provider store={store}>
          <ServiceContextProvider value={service}>
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
                <Route path='/drumsticks/:id' component={WrappedDrumStickEdit} />
                <Route path='/NotFound404' component={NotFoundPage} />
                <Redirect from='*' to='/NotFound404' />
              </Switch>
            </Router>
          </ServiceContextProvider>
        </Provider>
      </div>
    )
  }
}

export default App
