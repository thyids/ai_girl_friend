@echo off
chcp 65001 >nul
echo ==============================================
echo          Git 强制提交 & 覆盖远程
echo ==============================================
echo.

git add .
git commit -m "强制更新代码"
git push main main --force

echo.
echo 已强制覆盖远程仓库！
pause