require 'rubygems'
require 'cucumber'
require 'cucumber/rake/task'

task :default => [:features]

Cucumber::Rake::Task.new(:features) do |t|
  t.cucumber_opts = "features --tags ~@wip --format pretty"
end

Cucumber::Rake::Task.new(:wip) do |t|
  t.cucumber_opts = "features --tags @wip --format pretty"
end

Cucumber::Rake::Task.new(:html) do |t|
  t.cucumber_opts = "features --format html -o build/cli_tests.html"
end

