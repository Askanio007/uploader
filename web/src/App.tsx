import React from 'react';
import {AppBar, Box, Container, Tab, Tabs, Typography} from "@material-ui/core";
import {ImageSourceType} from "./service/UploadService";
import {LoadFromString} from "./LoadFromString";
import LoadFromFile from "./LoadFromFile";

function TabPanel(props:any) {
  const { children, value, index, ...other } = props;

  return (
      <Typography
          component="div"
          role="tabpanel"
          hidden={value !== index}
          id={`simple-tabpanel-${index}`}
          aria-labelledby={`simple-tab-${index}`}
          {...other}
      >
        {value === index && <Box p={3}>{children}</Box>}
      </Typography>
  );
}

function a11yProps(index:number) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}

function App() {
  const [value, setValue] = React.useState(0);

  const handleChange = (event:any, newValue:any) => {
    setValue(newValue);
  };
  return (
      <Container maxWidth="sm" style={{textAlign: "center"}}>
        <AppBar position="static">
          <Tabs value={value} onChange={handleChange} centered>
            <Tab label="file" {...a11yProps(0)} />
            <Tab label="url" {...a11yProps(1)} />
            <Tab label="base64" {...a11yProps(2)} />
          </Tabs>
        </AppBar>
        <TabPanel value={value} index={0}>
            <LoadFromFile />
        </TabPanel>
        <TabPanel value={value} index={1}>
          <LoadFromString typeSource={ImageSourceType.URL} title="From url" />
        </TabPanel>
        <TabPanel value={value} index={2}>
          <LoadFromString typeSource={ImageSourceType.BASE64} title="From base64" />
        </TabPanel>
      </Container>
  );
}

export default App;
