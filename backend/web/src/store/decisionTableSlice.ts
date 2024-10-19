import { createSlice } from '@reduxjs/toolkit';
import { DecisionTable } from '../models/decision-table.model';

export type DecisionTableState = {
  table: DecisionTable;
};

const initialState: DecisionTableState = {
  table: {
    columns: [],
    rows: [],
  },
};

export const DecisionTableSlice = createSlice({
  name: 'logical',
  initialState,
  reducers: {
    setTable: (state, action: { payload: DecisionTable; type: string }) => {
      state.table = action.payload;
    },
  },
});

export const { setTable } = DecisionTableSlice.actions;

export default DecisionTableSlice.reducer;
