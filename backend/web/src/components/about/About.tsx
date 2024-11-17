import { Box, Card, Typography } from '@mui/material';
import './About.scss';
import { Link } from 'react-router-dom';

const About = () => {
  return (
    <div className="about-frame">
      <Card className="about-block">
        <Typography variant="h4" color="primary">
          About the application
        </Typography>
        <Typography variant="body1">
          The application aims to enable the creation of{' '}
          <em>cause-effect graph</em> definitions in a textual format, which are
          then parsed into logical expressions. These expressions are
          simplified, optimized, and converted into{' '}
          <em>Disjunctive Normal Form</em> (DNF). Based on the DNF, a Decision
          Table is generated, serving as the foundation for subsequent exports.
        </Typography>
        <Typography>
          The exports serve as the application's outputs and are designed to
          facilitate the eventual generation of test cases. Currently, the only
          supported export format is <strong>GPT</strong> (General Predicate
          Testing).
        </Typography>
      </Card>
      <Card className="about-block">
        <Typography variant="h4" color="primary">
          About the project
        </Typography>
        <Typography variant="body1">
          This project serves as my thesis for the{' '}
          <strong>Computer Science MSc</strong> program. It was developed during
          the spring and autumn semesters of <strong>2024</strong>.
        </Typography>
      </Card>
      <Card className="about-block">
        <Typography variant="h4" color="primary">
          About the creator
        </Typography>
        <Typography variant="body1">
          My name is <strong>Tamas Karcag</strong>, the creator of this
          application. I am from <strong>Hungary</strong> and have been working
          in the IT field for several years.
        </Typography>
        <Typography variant="body1">
          <strong>My e-mail address</strong>:{' '}
          <a href="mailto:karcagtamas@outlook.com">karcagtamas@outlook.com</a>
          <br />
          <strong>My Github</strong>:{' '}
          <a href="https://github.com/karcagtamas">karcagtamas</a>
          <br />
          <strong>My LinkedIn</strong>:{' '}
          <a href="https://www.linkedin.com/in/karcag-tamas-2a3987197/">
            Karcag Tamas
          </a>
        </Typography>
      </Card>
      <Card className="about-block">
        <Typography variant="h4" color="primary">
          Let's start using
        </Typography>
        <Typography variant="body1">
          To start using the main functionality: <Link to="/">Click here</Link>
          <br />
          To read the documentation: <Link to="/docs">Click here</Link>
        </Typography>
      </Card>
      <Box sx={{ flexGrow: 1 }}></Box>
      <Card className="about-block">
        <Typography variant="h6" color="secondary">
          © All rights reserved 2024
        </Typography>
      </Card>
    </div>
  );
};

export default About;
