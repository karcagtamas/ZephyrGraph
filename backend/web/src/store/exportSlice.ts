import { createSlice } from '@reduxjs/toolkit';

export type ExportState = {
  content: string;
};

const initialState: ExportState = {
  content: '',
};

export const ExportSlice = createSlice({
  name: 'export',
  initialState,
  reducers: {
    setExport: (state, action: { payload: string; type: string }) => {
      state.content = action.payload;
    },
  },
});

export const { setExport } = ExportSlice.actions;

export default ExportSlice.reducer;
