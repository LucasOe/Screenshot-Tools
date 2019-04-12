const { app, BrowserWindow, ipcMain } = require('electron')

function createWindow () {
  let win = new BrowserWindow({
		width: 500,
		height: 500,
		titleBarStyle: 'hidden',
		backgroundColor: '#23272A',
		resizable: false
	})
	win.setMenu(null);
	win.webContents.openDevTools({detach:true});
  win.loadFile('index.html');
}

app.on('ready', createWindow)
