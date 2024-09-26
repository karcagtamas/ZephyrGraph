import { createSlice } from '@reduxjs/toolkit';

export type LogicalState = {
  definitions: string[];
};

const initialState: LogicalState = {
  definitions: [],
};

export const LogicalSlice = createSlice({
  name: 'logical',
  initialState,
  reducers: {
    setDefinitions: (state, action: { payload: string[]; type: string }) => {
      state.definitions = action.payload;
    },
  },
});

export const { setDefinitions } = LogicalSlice.actions;

export default LogicalSlice.reducer;
