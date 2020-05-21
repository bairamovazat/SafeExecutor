package ru.ivmiit.domjudge.connector.utils;

import java.util.HashMap;

public class ConfigInMemoryUtils {
    public static HashMap<String, Object> getConfig() {
        HashMap<String, Object> config = new HashMap<>();
        config.put("verification_required", "0");
        config.put("compile_penalty", "1");
        config.put("penalty_time", "20");
        config.put("results_prio", "{\"memory-limit\":99,\"output-limit\":99,\"run-error\":99,\"timelimit\":99,\"wrong-answer\":30,\"no-output\":10,\"correct\":1}");
        config.put("results_remap", "{}");
        config.put("score_in_seconds", "0");
        config.put("memory_limit", "524288");
        config.put("output_limit", "4096");
        config.put("process_limit", "64");
        config.put("sourcesize_limit", "256");
        config.put("sourcefiles_limit", "100");
        config.put("script_timelimit", "30");
        config.put("script_memory_limit", "2097152");
        config.put("script_filesize_limit", "540672");
        config.put("timelimit_overshoot", "1s|10%");
        config.put("output_storage_limit", "50000");
        config.put("output_display_limit", "2000");
        config.put("lazy_eval_results", "1");
        config.put("judgehost_warning", "30");
        config.put("judgehost_critical", "120");
        config.put("diskspace_error", "1048576");
        config.put("update_judging_seconds", "0");
        config.put("default_compare", "compare");
        config.put("default_run", "run");
        config.put("clar_categories", "{\"general\":\"General issue\",\"tech\":\"Technical issue\"}");
        config.put("clar_answers", "[\"No comment\",\"Read the problem statement carefully\"]");
        config.put("clar_queues", "{}");
        config.put("clar_default_problem_queue", "");
        config.put("show_pending", "0");
        config.put("show_flags", "1");
        config.put("show_affiliations", "1");
        config.put("show_affiliation_logos", "0");
        config.put("show_teams_submissions", "1");
        config.put("show_compile", "2");
        config.put("show_sample_output", "0");
        config.put("show_balloons_postfreeze", "0");
        config.put("show_relative_time", "0");
        config.put("time_format", "%H:%M");
        config.put("thumbnail_size", "128");
        config.put("show_limits_on_team_page", "0");
        config.put("team_column_width", "0");
        config.put("enable_printing", "0");
        config.put("registration_category_name", "");
        config.put("data_source", "0");
        config.put("auth_methods", "[]");
        config.put("allow_openid_auth", "0");
        config.put("openid_autocreate_team", "1");
        config.put("openid_provider", "https://accounts.google.com");
        config.put("openid_clientid", "");
        config.put("openid_clientsecret", "");
        config.put("ip_autologin", "0");
        return config;
    }
}
