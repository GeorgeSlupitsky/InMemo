import {
    FETCH_DATA,
    CLEAR_DATA,
    REMOVE_ITEM,
    ADD_ITEM_TO_ARRAY
} from './actions'

const initialState = {
    data: [],
    isLoading: true,
    checkedIds: []
}

export const itemListReducer = (state = initialState, action) => {
    const { data } = state
    switch (action.type) {
        case FETCH_DATA:
            return {
                ...state,
                isLoading: false,
                data: action.payload
            }
        case CLEAR_DATA:
            return {
                ...state,
                isLoading: true,
                checkedIds: [],
                data: []
            }
        case REMOVE_ITEM:
            const itemId = action.payload
            let updatedData = data.filter(i => i.id !== itemId)
            return {
                ...state,
                data: updatedData
            }
        case ADD_ITEM_TO_ARRAY:
            const id = action.payload
            let { checkedIds } = state
            const updatedItem = data.map(item => {
                if (item.id === id) {
                    if (!item.checked) {
                        checkedIds.push(item.id)
                    } else {
                        checkedIds = removeCheckedItemFromArray(checkedIds, item.id)
                    }
                    item.checked = !item.checked
                }
                return item
            })
            return {
                ...state,
                data: updatedItem,
                checkedIds: checkedIds
            }
        default: {
            return state
        }
    }
}

const removeCheckedItemFromArray = (arr, value) => {
    return arr.filter(function (ele) {
        return ele !== value
    })
}