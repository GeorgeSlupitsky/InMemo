import React, { Component } from 'react'
import './App.css'
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
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
        errorMessageYearRequired: 'Please enter a year or "-"',
        errorMessageYearPattern: 'Year must be within 1900-2099',
        errorMessageBooklet: 'Please choose booklet',
        errorMessageCountry: 'Please choose country',
        errorMessageType: 'Please choose type',
        errorMessageGroup: 'Please choose group',
        errorMessageOrder: 'Please choose order',
        selectLabelBooklet: 'Booklet',
        selectLabelCountry: 'Country',
        selectLabelType: 'Type',
        selectLabelGroup: 'Group',
        selectLabelOrder: 'Order',
        errorMessageDateRequired: 'Please enter date of the concert',
        errorMessageDatePattern: 'Date must be in format "DD.MM.YYYY"',
        errorMessageCity: 'Please choose city',
        errorMessageDescription: 'Please choose note',
        selectLabelCity: 'City',
        selectLabelDescription: 'Note'
      },
      es: {
        fileNameCDs: 'CD.',
        fileNameDrumSticks: 'Baquetas.',
        fileNameDrumSticksLabels: 'Baquetas (etiquetas).pdf',
        errorMessageYearRequired: 'Por favor ingrese un año o "-"',
        errorMessageYearPattern: 'El año debe estar dentro de 1900-2099',
        errorMessageBooklet: 'Por favor, elija el folleto',
        errorMessageCountry: 'Por favor, elija el país',
        errorMessageType: 'Por favor, elija el tipo',
        errorMessageGroup: 'Por favor, elija el grupo',
        errorMessageOrder: 'Por favor, elija orden',
        selectLabelBooklet: 'Folleto',
        selectLabelCountry: 'Pais',
        selectLabelType: 'Tipo',
        selectLabelGroup: 'Grupo',
        selectLabelOrder: 'Orden',
        errorMessageDateRequired: 'Por favor ingrese la fecha del concierto',
        errorMessageDatePattern: 'La fecha debe estar en formato "DD.MM.YYYY"',
        errorMessageCity: 'Por favor, elige la ciudad',
        errorMessageDescription: 'Por favor, elija una nota',
        selectLabelCity: 'Ciudad',
        selectLabelDescription: 'Nota'
      },
      ru: {
        fileNameCDs: 'Компакт диски.',
        fileNameDrumSticks: 'Барабанные палочки.',
        fileNameDrumSticksLabels: 'Барабанные палочки (лейблы).pdf',
        errorMessageYearRequired: 'Пожалуйста, укажите год или "-"',
        errorMessageYearPattern: 'Год должен быть в пределах 1900-2099',
        errorMessageBooklet: 'Пожалуйста, укажите буклет',
        errorMessageCountry: 'Пожалуйста, укажите страну',
        errorMessageType: 'Пожалуйста, укажите тип',
        errorMessageGroup: 'Пожалуйста, укажите класс',
        errorMessageOrder: 'Пожалуйста, укажите порядок',
        selectLabelBooklet: 'Буклет',
        selectLabelCountry: 'Страна',
        selectLabelType: 'Тип',
        selectLabelGroup: 'Класс',
        selectLabelOrder: 'Порядок',
        errorMessageDateRequired: 'Пожалуйста, укажите дату концерта',
        errorMessageDatePattern: 'Дата должна быть в формате "ДД.ММ.ГГГГ"',
        errorMessageCity: 'Пожалуйста, укажите город',
        errorMessageDescription: 'Пожалуйста, укажите примечание',
        selectLabelCity: 'Город',
        selectLabelDescription: 'Примечание'
      },
      uk: {
        fileNameCDs: 'Компакт диски.',
        fileNameDrumSticks: 'Барабанні палочки.',
        fileNameDrumSticksLabels: 'Барабанні палочки (лейбли).pdf',
        errorMessageYearRequired: 'Будь ласка, вкажіть рік або "-"',
        errorMessageYearPattern: 'Рік має бути в межах 1900-2099',
        errorMessageBooklet: 'Будь ласка, вкажіть буклет',
        errorMessageCountry: 'Будь ласка, вкажіть країну',
        errorMessageType: 'Будь ласка, вкажіть тип',
        errorMessageGroup: 'Будь ласка, вкажіть клас',
        errorMessageOrder: 'Будь ласка, вкажіть порядок',
        selectLabelBooklet: 'Буклет',
        selectLabelCountry: 'Країна',
        selectLabelType: 'Тип',
        selectLabelGroup: 'Клас',
        selectLabelOrder: 'Порядок',
        errorMessageDateRequired: 'Будь ласка, вкажіть дату концерту',
        errorMessageDatePattern: 'Дата має бути в форматі "ДД.ММ.РРРР"',
        errorMessageCity: 'Будь ласка, вкажіть місто',
        errorMessageDescription: 'Будь ласка, вкажіть примітку',
        selectLabelCity: 'Місто',
        selectLabelDescription: 'Примітка'
      },
      ja: {
        fileNameCDs: 'CD.',
        fileNameDrumSticks: 'ドラムスティック.',
        fileNameDrumSticksLabels: 'ドラムスティック (ラベル).pdf',
        errorMessageYearRequired: '年または「 - 」を入力してください',
        errorMessageYearPattern: '年は1900-2099以内でなければなりません',
        errorMessageBooklet: '小冊子を選択してください',
        errorMessageCountry: '国を選択してください',
        errorMessageType: 'タイプを選択してください',
        errorMessageGroup: 'グループを選択してください',
        errorMessageOrder: '注文を選択してください',
        selectLabelBooklet: '小冊子',
        selectLabelCountry: '国',
        selectLabelType: 'タイプ',
        selectLabelGroup: 'グループ',
        selectLabelOrder: '注文',
        errorMessageDateRequired: 'コンサートの日を入力してください',
        errorMessageDatePattern: '日付は「DD.MM.YYYY」の形式でなければなりません',
        errorMessageCity: '市を選択してください',
        errorMessageDescription: '注意を選択してください',
        selectLabelCity: '市',
        selectLabelDescription: 'メモ'
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
      return (<CDEditContainer {...props} url='/cds' localizedMessages={localizedMessages} itemId={props.match.params.id}/>)
    }
    const WrappedCDEditForForeign = function (props) {
      return (<CDEditContainer {...props} url='/cdsForeign' localizedMessages={localizedMessages} itemId={props.match.params.id}/>)
    }
    const WrappedCDEditForDomestic = function (props) {
      return (<CDEditContainer {...props} url='/cdsDomestic' localizedMessages={localizedMessages} itemId={props.match.params.id}/>)
    }
    const WrappedDrumStickEdit = function (props) {
      return (<DrumStickEditContainer {...props} localizedMessages={localizedMessages} itemId={props.match.params.id}/>)
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
              </Switch>
            </Router>
          </ServiceContextProvider>
        </Provider>
      </div>
    )
  }
}

export default App
