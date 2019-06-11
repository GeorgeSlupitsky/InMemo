import React, { Component } from 'react'
import './App.css'
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import CDEdit from '../components/items/CDEdit'
import DrumStickEdit from '../components/items/DrumStickEdit'
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
    let localizedMessages = new LocalizedStrings({
      en: {
        fileNameCDs: 'CD.',
        fileNameDrumSticks: 'Drumsticks.',
        fileNameDrumSticksLabels: 'Drumsticks (labels).pdf',
      },
      es: {
        fileNameCDs: 'CD.',
        fileNameDrumSticks: 'Baquetas.',
        fileNameDrumSticksLabels: 'Baquetas (etiquetas).pdf',
      },
      ru: {
        fileNameCDs: 'Компакт диски.',
        fileNameDrumSticks: 'Барабанные палочки.',
        fileNameDrumSticksLabels: 'Барабанные палочки (лейблы).pdf',
      },
      uk: {
        fileNameCDs: 'Компакт диски.',
        fileNameDrumSticks: 'Барабанні палочки.',
        fileNameDrumSticksLabels: 'Барабанні палочки (лейбли).pdf',
      },
      ja: {
        fileNameCDs: 'CD.',
        fileNameDrumSticks: 'ドラムスティック.',
        fileNameDrumSticksLabels: 'ドラムスティック (ラベル).pdf',
      }
    })

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
      return (<CDEdit {...props} url='/cds' localizedMessages={localizedMessages} />)
    }
    const WrappedCDEditForForeign = function (props) {
      return (<CDEdit {...props} url='/cdsForeign' localizedMessages={localizedMessages} />)
    }
    const WrappedCDEditForDomestic = function (props) {
      return (<CDEdit {...props} url='/cdsDomestic' localizedMessages={localizedMessages} />)
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
                <Route path='/drumsticks/:id' component={DrumStickEdit} />
              </Switch>
            </Router>
          </ServiceContextProvider>
        </Provider>
      </div>
    )
  }
}

export default App
