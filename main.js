const { app, BrowserWindow, ipcMain } = require('electron');

function createWindow () {
	let win = new BrowserWindow({
		width: 500,
		height: 800,
		titleBarStyle: 'hidden',
		backgroundColor: '#23272A',
		resizable: false
	});
	win.setMenuBarVisibility(false);
	win.loadFile('index.html');

	win.on('closed', () => {
		win = null;
	});
}

app.on('ready', createWindow);

app.on('window-all-closed', () => {
  app.quit();
});
