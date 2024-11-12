import { Typography } from '@mui/material';
import SyntaxHighlighter from 'react-syntax-highlighter';
import { vs } from 'react-syntax-highlighter/dist/esm/styles/prism';
import { logicalOperators, source } from './source';

const Docs = () => {
  return (
    <div>
      {source.map((src) => {
        return (
          <div>
            <Typography variant="h6" color="primary">
              {src.caption}
            </Typography>
            <Typography variant="body2">{src.text}</Typography>
            <SyntaxHighlighter
              language="kotlin"
              style={vs}
              showLineNumbers={true}
            >
              {src.code}
            </SyntaxHighlighter>
            {src.children.map((ch) => {
              return (
                <div>
                  <Typography variant="body2">{ch.text}</Typography>
                  <SyntaxHighlighter
                    language="kotlin"
                    style={vs}
                    showLineNumbers={true}
                  >
                    {ch.code}
                  </SyntaxHighlighter>
                </div>
              );
            })}
          </div>
        );
      })}
      <Typography variant="h6" color="primary">
        Logical Operators
      </Typography>
      <table>
        <thead>
          <tr>
            <th>
              <Typography variant="body2" color="secondary">
                Operator
              </Typography>
            </th>
            <th>
              <Typography variant="body2" color="secondary">
                Description
              </Typography>
            </th>
            <th>
              <Typography variant="body2" color="secondary">
                Types
              </Typography>
            </th>
            <th>
              <Typography variant="body2" color="secondary">
                Example
              </Typography>
            </th>
          </tr>
        </thead>
        <tbody>
          {logicalOperators.map((op) => {
            return (
              <tr>
                <td>
                  <Typography variant="body2" color="primary">
                    {op.operator}
                  </Typography>
                </td>
                <td>
                  <Typography variant="body2">{op.description}</Typography>
                </td>
                <td>
                  <Typography variant="body2">{op.types.join(', ')}</Typography>
                </td>
                <td>
                  <Typography variant="body2">
                    <i>{op.example}</i>
                  </Typography>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
};

export default Docs;
