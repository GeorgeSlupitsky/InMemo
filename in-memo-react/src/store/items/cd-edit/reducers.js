import {
  FETCH_CD_ENUMS_DATA,
  FETCH_DATA_ITEM_SUCCESS,
  FETCH_DATA_ITEM_ERROR,
  CLEAR_DATA,
  SAVE_ITEM_TO_STATE,
  SAVE_BAND_MEMBER_TO_STATE,
  POST_PUT_ITEM
} from './actions'

let emptyItem = {
  band: {
    name: '',
    order: '',
    bandMembers: []
  },
  album: '',
  year: '',
  booklet: '',
  countryEdition: '',
  cdType: '',
  cdGroup: '',
  autograph: false,
  discogsLink: ''
}

const initialState = {
  bandMember: '',
  isLoadingItem: true,
  isLoadingEnums: true,
  item: emptyItem,
  cdEnums: {}
}

export const cdEditReducer = (state = initialState, action) => {
  switch (action.type) {
    case FETCH_CD_ENUMS_DATA:
      return {
        ...state,
        isLoadingEnums: false,
        cdEnums: action.payload
      }
    case FETCH_DATA_ITEM_SUCCESS:
      return {
        ...state,
        isLoadingItem: false,
        item: action.payload
      }
    case FETCH_DATA_ITEM_ERROR:
      return {
        ...state
      }
    case CLEAR_DATA:
      return {
        ...state,
        isLoadingItem: true,
        isLoadingEnums: true,
        item: emptyItem,
        cdEnums: {}
      }
    case SAVE_ITEM_TO_STATE:
      return {
        ...state,
        item: action.payload
      }
    case SAVE_BAND_MEMBER_TO_STATE:
      return {
        ...state,
        bandMember: action.payload
      }
    case POST_PUT_ITEM:
      return {
        ...state
      }
    default:
      return state
  }
}

