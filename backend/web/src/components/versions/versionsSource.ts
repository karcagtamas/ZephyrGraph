import { LocalDate } from '../../core/date.helper';

type Version = {
  name: string;
  date: LocalDate;
  items: string[];
};

export const versions: Version[] = [
  {
    name: "Version 2.3.0",
    date: {
      year: 2024,
      month: 11,
      day: 18,
    },
    items: [
      "Extended graph controller on the backend API",
      "Added detailed examples",
      "Added app name and icon",
      "Fixed GPT export",
      "Added About and Versions pages"
    ],
  },
  {
    name: 'Version 2.2.0',
    date: {
      year: 2024,
      month: 11,
      day: 12,
    },
    items: [
      'New graph documentation has been added',
      'Added application client style theming',
      'Introduced variable types',
      'Added closed-range and open-ended range intervals',
      'Appended a new Export tab and generated GPT results',
      'Added snackbar messages for major actions',
      'Enhanced DSL with simplified literals',
    ],
  },
  {
    name: 'Version 2.1.0',
    date: {
      year: 2024,
      month: 11,
      day: 5,
    },
    items: ['Added BVA support on backend side'],
  },
  {
    name: 'Version 2.0.0',
    date: {
      year: 2024,
      month: 10,
      day: 20,
    },
    items: [
      'Enhanced deployment process with Docker support',
      'Enhanced logical definitions and added optimizer and simplifier refiners',
      'Enhanced Decision Table generator',
      'Added message board',
      'Enhanced logical expressions with math operators',
      'Added type checkers for the graph builders',
      'Added more detailed error messages',
    ],
  },
  {
    name: 'Version 1.2.0',
    date: {
      year: 2024,
      month: 10,
      day: 3,
    },
    items: [
      'Introduced Decision Table generation',
      'Added DNF refiner',
      'Introduced logical expressions',
    ],
  },
  {
    name: 'Version 1.1.0',
    date: {
      year: 2024,
      month: 9,
      day: 30,
    },
    items: [
      'Enhanced OR and AND logical definitions and their DSL',
      'Refined effect handling',
      'Introduced logical transformation with history',
      'Added tabbed client view',
      'Added CNF refiner',
      'Upgraded Kotlin Language Server with graph model',
    ],
  },
  {
    name: 'Version 1.0.0',
    date: {
      year: 2024,
      month: 9,
      day: 9,
    },
    items: [
      'Added graph language and made it executable',
      'Added Kotlin Language Server',
      'Added Editor on client side',
    ],
  },
];
