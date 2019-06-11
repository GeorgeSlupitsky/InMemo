import { combineReducers } from 'redux'
import { buttonsPanelReducer } from './common/buttons-panel/reducers'
import { itemListReducer } from './items/item-list/reducers'

export default combineReducers({
    buttonsPanel: buttonsPanelReducer,
    itemList: itemListReducer
})