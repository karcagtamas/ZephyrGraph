import { createSlice } from '@reduxjs/toolkit';

type Type = 'error' | 'warning' | 'success' | 'info';

export type SnackbarState = {
  message: string;
  type: Type;
  isOpen: boolean;
};

const initialState: SnackbarState = {
  message: '',
  type: 'info',
  isOpen: false,
};

export const SnackbarSlice = createSlice({
  name: 'snackbar',
  initialState,
  reducers: {
    addInfo: (state, action: { payload: string; type: string }) => {
      state.message = action.payload;
      state.type = 'info';
      state.isOpen = true;
    },
    addSuccess: (state, action: { payload: string; type: string }) => {
      state.message = action.payload;
      state.type = 'success';
      state.isOpen = true;
    },
    addWarning: (state, action: { payload: string; type: string }) => {
      state.message = action.payload;
      state.type = 'warning';
      state.isOpen = true;
    },
    addError: (state, action: { payload: string; type: string }) => {
      state.message = action.payload;
      state.type = 'error';
      state.isOpen = true;
    },
    add: (
      state,
      action: { payload: { message: string; type: Type }; type: string }
    ) => {
      state.message = action.payload.message;
      state.type = action.payload.type;
      state.isOpen = true;
    },
    close: (state) => {
      state.isOpen = false;
    },
  },
});

export const { addInfo, addSuccess, addError, addWarning, add, close } =
  SnackbarSlice.actions;

export default SnackbarSlice.reducer;
