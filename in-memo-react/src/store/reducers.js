import { combineReducers } from 'redux'
import { buttonsPanelReducer } from './common/buttons-panel/reducers'
import { itemListReducer } from './items/item-list/reducers'
import { cdEditReducer } from './items/cd-edit/reducers'
import { drumStickEditReducer } from './items/drumstick-edit/reducers'

export default combineReducers({
    buttonsPanel: buttonsPanelReducer,
    itemList: itemListReducer,
    cdEdit: cdEditReducer,
    drumStickEdit: drumStickEditReducer
})