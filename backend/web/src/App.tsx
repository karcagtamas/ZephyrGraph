import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import './App.scss';
import TabView from './frame/TabView';
import {
  Alert,
  AppBar,
  Box,
  createTheme,
  Snackbar,
  ThemeProvider,
  Toolbar,
  Typography,
} from '@mui/material';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from './store/store';
import { close } from './store/snackbarSlice';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Docs from './components/docs/Docs';
import About from './components/about/About';
import Scrollable from './components/common/Scrollable';
import Versions from './components/versions/Versions';

const version = import.meta.env.VITE_VERSION;

const queryClient = new QueryClient();

const theme = createTheme({
  typography: {
    fontFamily: 'Fira Code',
  },
  palette: {
    primary: {
      main: '#2b0883',
    },
    secondary: {
      main: '#02663b',
    },
  },
  components: {
    MuiButton: {
      defaultProps: {
        size: 'small',
      },
    },
    MuiFilledInput: {
      defaultProps: {
        margin: 'dense',
      },
    },
    MuiFormControl: {
      defaultProps: {
        margin: 'dense',
      },
    },
    MuiFormHelperText: {
      defaultProps: {
        margin: 'dense',
      },
    },
    MuiIconButton: {
      defaultProps: {
        size: 'small',
      },
    },
    MuiInputBase: {
      defaultProps: {
        margin: 'dense',
      },
    },
    MuiInputLabel: {
      defaultProps: {
        margin: 'dense',
      },
    },
    MuiListItem: {
      defaultProps: {
        dense: true,
      },
    },
    MuiOutlinedInput: {
      defaultProps: {
        margin: 'dense',
      },
    },
    MuiFab: {
      defaultProps: {
        size: 'small',
      },
    },
    MuiTable: {
      defaultProps: {
        size: 'small',
      },
    },
    MuiTextField: {
      defaultProps: {
        margin: 'dense',
      },
    },
    MuiToolbar: {
      defaultProps: {
        variant: 'dense',
      },
    },
    MuiAlert: {
      defaultProps: {
        sx: {
          paddingTop: 0,
          paddingBottom: 0,
        },
      },
    },
  },
});

const router = createBrowserRouter([
  {
    path: '/',
    element: <TabView />,
  },
  {
    path: '/docs',
    element: (
      <Scrollable>
        <Docs />
      </Scrollable>
    ),
  },
  {
    path: '/about',
    element: (
      <Scrollable>
        <About />
      </Scrollable>
    ),
  },
  {
    path: '/versions',
    element: (
      <Scrollable>
        <Versions />
      </Scrollable>
    ),
  },
]);

function App() {
  const snackbar = useSelector((state: RootState) => state.snackbar);
  const dispatch = useDispatch();

  const handleSnackbarClose = () => {
    dispatch(close());
  };

  return (
    <ThemeProvider theme={theme}>
      <QueryClientProvider client={queryClient}>
        <AppBar position="static">
          <Toolbar>
            <img src="/logo_inverse.svg" title="logo" alt="logo" />
            <Box sx={{ width: '30px' }} />
            <a href="/about">
              <Typography variant="h6" color="inherit" component="div">
                ZephyrGraph
              </Typography>
            </a>
            <Box sx={{ flexGrow: 1 }} />
            <a href="/versions">
              <Typography variant="body1" color="inherit" component="div">
                Version: {version}
              </Typography>
            </a>
          </Toolbar>
        </AppBar>
        <RouterProvider router={router} />
        <Snackbar
          open={snackbar.isOpen}
          onClose={handleSnackbarClose}
          autoHideDuration={5000}
          anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
        >
          <Alert
            severity={snackbar.type}
            sx={{ p: 1, paddingRight: 2, paddingLeft: 2 }}
          >
            {snackbar.message}
          </Alert>
        </Snackbar>
      </QueryClientProvider>
    </ThemeProvider>
  );
}

export default App;
