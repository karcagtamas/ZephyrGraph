import { useSelector } from 'react-redux';
import CNFRow from './CnfRow';
import { RootState } from '../../store/store';
import './LogicalPanel.scss';

const LogicalPanel = () => {
  const definitions = useSelector(
    (state: RootState) => state.logical.definitions
  );

  return (
    <div className="logical-rows">
      <div className="title">
        <strong>CNF logical formulas:</strong>
      </div>
      {definitions.map((def, idx) => {
        return <CNFRow key={idx} definition={def} index={idx}></CNFRow>;
      })}
    </div>
  );
};

export default LogicalPanel;
