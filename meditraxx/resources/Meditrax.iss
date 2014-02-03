;This file will be executed next to the application bundle image
;I.e. current directory will contain folder Meditrax with application files
#define MyAppName "Meditrax"
#define MyAppVersion "1.0"
#define MyAppPublisher "Panda Technologies"
#define MyAppURL "http://www.pravatpanda.com/"
#define MyAppExeName "Meditrax.exe"
[Setup]
AppId={{fxApplication}}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
AppCopyright=
;AppPublisherURL=http://java.com/
;AppSupportURL=http://java.com/
;AppUpdatesURL=http://java.com/
DefaultDirName={pf}\Meditrax
DefaultGroupName=Panda Technologies
;Optional License
LicenseFile=
;WinXP or above
MinVersion=0,5.1 
OutputBaseFilename=Meditrax-1.0
Compression=lzma
SolidCompression=yes
PrivilegesRequired=lowest
SetupIconFile=Meditrax\Meditrax.ico
UninstallDisplayIcon={app}\Meditrax.ico
UninstallDisplayName=Meditrax
WizardImageStretch=No
WizardSmallImageFile=Meditrax-setup-icon.bmp   

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Files]
Source: "Meditrax\Meditrax.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "Meditrax\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{group}\Meditrax"; Filename: "{app}\Meditrax.exe"; IconFilename: "{app}\Meditrax.ico"; Check: returnTrue()
Name: "{commondesktop}\Meditrax"; Filename: "{app}\Meditrax.exe";  IconFilename: "{app}\Meditrax.ico"; Check: returnFalse()

[Run]
Filename: "{app}\Meditrax.exe"; Description: "{cm:LaunchProgram,Meditrax}"; Flags: nowait postinstall skipifsilent

[Code]
function returnTrue(): Boolean;
begin
  Result := True;
end;

function returnFalse(): Boolean;
begin
  Result := False;
end;

function InitializeSetup(): Boolean;
begin
// Possible future improvements:
//   if version less or same => just launch app
//   if upgrade => check if same app is running and wait for it to exit
//   Add pack200/unpack200 support? 
  Result := True;
end;  
