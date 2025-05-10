import { marked } from 'marked';

const markdownData =
    `# 更新日志
## 1.0.0 (2021-09-28)
### 新增功能
- 新增功能1
- 新增功能2
    `.trim()
const homeContent =
    `# 主页
##  欢迎使用核心计划平台，这里是您核心计划的主页，您可以快速访问您需要的功能，包括模块介绍、快速导航、版本信息等。
- [模块介绍](/module)
- [快速开始](/quick)
- [版本信息](/version)
`.trim();
export default markdownData
export { markdownData, homeContent };







