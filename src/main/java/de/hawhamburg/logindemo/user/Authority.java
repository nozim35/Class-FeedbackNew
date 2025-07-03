package de.hawhamburg.logindemo.user;

/**
 * Sub-Entity and Domain Object internally used by {@link User}.
 */
record Authority(String role) {}