import { Card, Typography } from '@mui/material';
import './Versions.scss';
import { versions } from './versionsSource';
import { localDateConverter } from '../../core/date.helper';

const Versions = () => {
  return (
    <div className="versions-frame">
      {versions.map((version, idx) => {
        return (
          <Card key={idx} className="version-block">
            <Typography variant="h5" color="primary">
              {version.name}
            </Typography>
            <Typography variant="h6" color="secondary">
              {localDateConverter.toString(version.date)}
            </Typography>
            <ul>
              {version.items.map((item, i) => {
                return (
                  <li key={i}>
                    <Typography variant="body1">{item}</Typography>
                  </li>
                );
              })}
            </ul>
          </Card>
        );
      })}
    </div>
  );
};

export default Versions;
