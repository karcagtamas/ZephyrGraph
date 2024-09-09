import { createSlice } from '@reduxjs/toolkit';

export type WarningState = {
  message: string | undefined;
};

const initialState: WarningState = {
  message: undefined,
};

export const WarningSlice = createSlice({
  name: 'warning',
  initialState,
  reducers: {
    setWarning: (state, action: { payload: string; type: string }) => {
      state.message = action.payload;
    },
    clearWarning: (state) => {
      state.message = undefined;
    },
  },
});

export const { setWarning, clearWarning } = WarningSlice.actions;

export default WarningSlice.reducer;
