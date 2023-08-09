import { createAction, handleActions } from "redux-actions";

const START_LOADING = "loading/START_LOADING";
const END_LOADING = "loading/END_LOADING";

export const startLoading = createAction(
  START_LOADING,
  (actionType) => actionType
);
export const endLoading = createAction(END_LOADING, (actionType) => actionType);

const initialState = {};

//리듀서
const loading = handleActions(
  {
    [START_LOADING]: (state, action) => ({
      ...state,
      [action.payload]: true,
    }),
    [END_LOADING]: (state, action) => ({
      ...state,
      [action.payload]: false,
    }),
  },
  initialState
);

export default loading;
