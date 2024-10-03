import { useSelector } from 'react-redux';
import { RootState } from '../../../store/store';
import { DecisionTableItem } from '../../../models/decision-table.model';
import './DecisionTable.scss';
import { Paper } from '@mui/material';

const itemStringifier = (item: DecisionTableItem | null): string => {
  switch (item) {
    case DecisionTableItem.True:
      return '1';
    case DecisionTableItem.False:
      return '0';
    case DecisionTableItem.NotUsed:
      return '-';
    default:
      return '';
  }
};

const DecisionTable = () => {
  const table = useSelector((state: RootState) => state.decisionTable);

  return (
    <Paper className="table-frame">
      <table id="decisionTable">
        <thead>
          <tr>
            <th>-</th>
            {table.table.columns.map((col) => (
              <th key={col}>{col}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {table.table.rows.map((row, idx) => (
            <tr key={idx}>
              <th className="left">{row.node.displayName}</th>
              {row.items
                .map((item) => itemStringifier(item))
                .map((item, idx) => (
                  <td key={idx}>{item}</td>
                ))}
            </tr>
          ))}
        </tbody>
      </table>
    </Paper>
  );
};

export default DecisionTable;
