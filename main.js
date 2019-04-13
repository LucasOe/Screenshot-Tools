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
}

app.on('ready', createWindow);
