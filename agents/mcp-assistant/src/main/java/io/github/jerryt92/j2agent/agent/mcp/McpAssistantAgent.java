package io.github.jerryt92.j2agent.agent.mcp;

import io.github.jerryt92.j2agent.agent.mcp.prompts.McpAssistantPrompts;
import io.github.jerryt92.j2agent.service.llm.agent.inf.AiAgent;
import io.github.jerryt92.j2agent.service.llm.agent.inf.constant.AgentThinkingOverride;
import io.github.jerryt92.j2agent.service.llm.agent.inf.feature.ExternalSkills;
import io.github.jerryt92.j2agent.service.llm.agent.inf.feature.McpFeature;
import io.github.jerryt92.j2agent.tools.MathTool;
import io.github.jerryt92.j2agent.tools.WebTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * MCP 接入助手 Agent
 */
@Slf4j
@Component
public class McpAssistantAgent extends AiAgent implements ExternalSkills, McpFeature {
    private final MathTool mathTool;
    private final WebTool webTool;

    @Override
    public String getAgentId() {
        return "mcp_assistant";
    }

    @Override
    public String getAgentName() {
        return "MCP接入助手";
    }

    @Override
    public String getAgentDescription() {
        return "通过 MCP 调用已接入的外部工具与服务";
    }

    @Override
    public String getDispatchPrompt() {
        return """
                J2Agent MCP 接入助手；调用平台已连接的 MCP 工具完成用户任务，辅以数学计算与网页检索。
                典型问法：使用某 MCP 服务能力（如查票、查数据、调外部 API）、需要工具调用的自动化问题。""";
    }

    @Override
    public String loadSystemPrompt() {
        return McpAssistantPrompts.SYSTEM_PROMPT;
    }

    @Override
    public int getSort() {
        return 3;
    }

    @Override
    public String getLogo() {
        return "🔌";
    }

    @Override
    public boolean isQaTemplateEnabled() {
        return true;
    }

    @Override
    public AgentThinkingOverride getThinkingOverride() {
        return AgentThinkingOverride.PROVIDER_DEFAULT;
    }

    public McpAssistantAgent(MathTool mathTool, WebTool webTool) {
        this.mathTool = mathTool;
        this.webTool = webTool;
    }

    @Override
    protected Object[] buildTools() {
        return new Object[]{mathTool, webTool};
    }
}
