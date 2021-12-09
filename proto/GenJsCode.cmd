@echo off

set "_pbjs=node C:\Users\Administrator\AppData\Roaming\npm\node_modules\protobufjs\bin\pbjs"
set "_pbts=node C:\Users\Administrator\AppData\Roaming\npm\node_modules\protobufjs\bin\pbts"

%_pbjs% -t static-module -w commonjs --es6 --keep-case -o .\out\GameMsgProtocol.js E:\workplace\wjy\netty-game\src\main\java\com\zzn\nettygame\netty\proto\GameMsgProtocol.proto
%_pbts% -o .\out\GameMsgProtocol.d.ts .\out\GameMsgProtocol.js