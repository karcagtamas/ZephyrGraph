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
            <Typography variant="h6" color="inherit" component="div">
              ZephyrGraph
            </Typography>
            <Box sx={{ flexGrow: 1 }} />
            <Typography variant="body1" color="inherit" component="div">
              Version: {version}
            </Typography>
          </Toolbar>
        </AppBar>
        <TabView />
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
