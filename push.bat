@echo off
chcp 65001 >nul
echo ==============================================
echo           Git 正常提交（只更新修改的文件）
echo ==============================================
echo.

git add .
git commit -m "更新代码"
git push main main

echo.
echo 提交成功！GitHub 已同步你修改的文件～
pause